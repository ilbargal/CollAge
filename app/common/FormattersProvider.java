package common;

import play.data.format.Formatters;
import play.data.format.Formatters.SimpleFormatter;
import play.i18n.MessagesApi;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by Daniel on 30/05/2016.
 * This and the formattersModule class exists to convert string to timestamp
 * in play forms. Ask Daniel for more info.
 */
@Singleton
public class FormattersProvider implements Provider<Formatters> {


    private final MessagesApi messagesApi;

    @Inject
    public FormattersProvider(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    @Override
    public Formatters get() {
        Formatters formatters = new Formatters(messagesApi);

        formatters.register(Timestamp.class, new SimpleFormatter<Timestamp>() {

            @Override
            public Timestamp parse(String input, Locale l) throws ParseException {

                // Birthday fix
                String birthDayStr =  input;
                birthDayStr = birthDayStr.substring(0, birthDayStr.indexOf('T')) + " 00:00:00";
                Timestamp birthDay = Timestamp.valueOf(birthDayStr);

                return birthDay;
            }

            @Override
            public String print(Timestamp localTime, Locale l) {
                return localTime.toString();
            }

        });

        return formatters;
    }
}
