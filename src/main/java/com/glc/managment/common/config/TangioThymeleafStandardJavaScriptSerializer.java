package com.glc.managment.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.glc.managment.common.json.ModelSerializationFilter;
import com.glc.managment.common.json.SerializationMarker;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.standard.serializer.IStandardJavaScriptSerializer;

import javax.naming.ConfigurationException;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TangioThymeleafStandardJavaScriptSerializer implements IStandardJavaScriptSerializer {

    private final ObjectMapper mapper;

    TangioThymeleafStandardJavaScriptSerializer() throws ConfigurationException {

        this.mapper = new ObjectMapper();
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.mapper.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
        this.mapper.enable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        this.mapper.getFactory().setCharacterEscapes(new JacksonThymeleafCharacterEscapes());
        this.mapper.setDateFormat(new JacksonThymeleafISO8601DateFormat());

        this.mapper.setFilterProvider(new SimpleFilterProvider().addFilter(SerializationMarker.MODEL_SERIALIZATION_FILTER_NAME, new ModelSerializationFilter()));

        // JSR310 support for Jackson is present in classpath
        try {
            this.mapper.registerModule((Module)JavaTimeModule.class.newInstance());
            this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        } catch (final InstantiationException e) {
            throw new ConfigurationException("Exception while trying to initialize JSR310 support for Jackson");
        } catch (final IllegalAccessException e) {
            throw new ConfigurationException("Exception while trying to initialize JSR310 support for Jackson");
        }

    }

    @Override
    public void serializeValue(Object object, Writer writer) {
        try {
            this.mapper.writeValue(writer, object);
        } catch (final IOException e) {
            throw new TemplateProcessingException(
                    "An exception was raised while trying to serialize object to JavaScript using Jackson", e);
        }
    }

    /*
 * This DateFormat implementation replaces the standard Jackson date serialization mechanism for ISO6801 dates,
 * with the aim of making Jackson output dates in a way that is at the same time ECMAScript-valid and also
 * as compatible with non-Jackson JavaScript serialization infrastructure in Thymeleaf as possible. For this:
 *
 *   * The default Jackson behaviour of outputting all dates as GMT is disabled.
 *   * The default Jackson format adding timezone as '+0800' is modified, as ECMAScript requires '+08:00'
 *
 * On the latter point, see https://github.com/FasterXML/jackson-databind/issues/1020
 */
    private static final class JacksonThymeleafISO8601DateFormat extends DateFormat {

        private static final long serialVersionUID = 1354081220093875129L;

        /*
         * This SimpleDateFormat defines an almost-ISO8601 formatter.
         *
         * The correct ISO8601 format would be "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", but the "X" pattern (which outputs the
         * timezone as "+02:00" or "Z" instead of "+0200") was not added until Java SE 7. So the use of this
         * SimpleDateFormat object requires additional post-processing.
         *
         * SimpleDateFormat objects are NOT thread-safe, but it is here being used from another DateFormat
         * implementation, so we must suppose that it is the use of this DateFormat wrapper that will be
         * adequately synchronized by Jackson.
         */
        private SimpleDateFormat dateFormat;


        JacksonThymeleafISO8601DateFormat() {
            super();
            this.dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ");
            setCalendar(this.dateFormat.getCalendar());
            setNumberFormat(this.dateFormat.getNumberFormat());
        }


        @Override
        public StringBuffer format(final Date date, final StringBuffer toAppendTo, final FieldPosition fieldPosition) {
            final StringBuffer formatted = this.dateFormat.format(date, toAppendTo, fieldPosition);
            formatted.insert(26, ':');
            return formatted;
        }


        @Override
        public Date parse(final String source, final ParsePosition pos) {
            throw new UnsupportedOperationException(
                    "JacksonThymeleafISO8601DateFormat should never be asked for a 'parse' operation");
        }


        @Override
        public Object clone() {
            JacksonThymeleafISO8601DateFormat other = (JacksonThymeleafISO8601DateFormat) super.clone();
            other.dateFormat = (SimpleDateFormat) dateFormat.clone();
            return other;
        }


    }



    /*
     * This CharacterEscapes implementation makes sure that the slash ('/') and ampersand ('&') characters
     * are also escaped, which is not standard Jackson behaviour.
     *
     * Escaping '/' covers against the possible premature closing of <script> tags inside inlined JavaScript
     * literals, thus preventing code injection in templates being processed by browsers as HTML.
     *
     * Escaping '&' covers against the injection of XHTML-escaped code which might prematurely close the
     * inlined JavaScript literals and even the container <script> tags in templates being processed
     * by browsers as XHTML.
     *
     * Note that, unfortunately Jackson's escape customization mechanism offers no way to only escape '/' when it
     * is preceded by '<', so that only '</' is escaped. Therefore, all '/' need to be escaped. Which is a
     * difference with the default Unbescape-based mechanism.
     */
    private static final class JacksonThymeleafCharacterEscapes extends CharacterEscapes {

        private static final int[] CHARACTER_ESCAPES;
        private static final SerializableString SLASH_ESCAPE;
        private static final SerializableString AMPERSAND_ESCAPE;

        static {

            CHARACTER_ESCAPES = CharacterEscapes.standardAsciiEscapesForJSON();
            CHARACTER_ESCAPES['/'] = CharacterEscapes.ESCAPE_CUSTOM;
            CHARACTER_ESCAPES['&'] = CharacterEscapes.ESCAPE_CUSTOM;

            SLASH_ESCAPE = new SerializedString("\\/");
            AMPERSAND_ESCAPE = new SerializedString("\\u0026");

        }

        JacksonThymeleafCharacterEscapes() {
            super();
        }

        @Override
        public int[] getEscapeCodesForAscii() {
            return CHARACTER_ESCAPES;
        }

        @Override
        public SerializableString getEscapeSequence(final int ch) {
            if (ch == '/') {
                return SLASH_ESCAPE;
            }
            if (ch == '&') {
                return AMPERSAND_ESCAPE;
            }
            return null;
        }

    }

}