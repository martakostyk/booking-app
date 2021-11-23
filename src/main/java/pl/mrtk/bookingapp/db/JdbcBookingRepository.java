package pl.mrtk.bookingapp.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.mrtk.bookingapp.booking.ImmutableBookingDto;
import pl.mrtk.bookingapp.booking.BookingData;
import pl.mrtk.bookingapp.booking.BookingDto;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JdbcBookingRepository implements BookingRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcBookingRepository.class);

    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DATE_TIME = "date_time";
    private static final String COLUMN_CREATION_TIMESTAMP = "creation_timestamp";

    private static final String SQL_QUERY_ALL = "SELECT * FROM bookings";
    private static final String SQL_INSERT = String.format("INSERT INTO bookings (%s, %s, %s) VALUES (?, ?, ?)",
            COLUMN_NAME, COLUMN_DATE_TIME, COLUMN_CREATION_TIMESTAMP);

    private final DataSource dataSource;

    public JdbcBookingRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(BookingData bookingData) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setString(1, bookingData.getName());
            preparedStatement.setObject(2, bookingData.getDateTime());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.warn("Cannot add booking into the database, cause:", exception);
        }
    }

    @Override
    public Set<BookingDto> getAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_QUERY_ALL)) {
            Set<BookingDto> bookings = new HashSet<>();
            while (resultSet.next()) {
                BookingDto booking = ImmutableBookingDto.builder()
                        .name(resultSet.getString(COLUMN_NAME))
                        .dateTime((LocalDateTime) resultSet.getObject(COLUMN_DATE_TIME))
                        .creationTimestamp(resultSet.getTimestamp(COLUMN_CREATION_TIMESTAMP).toLocalDateTime())
                        .build();
                bookings.add(booking);
            }
            return bookings;
        } catch (SQLException exception) {
            log.warn("Cannot get bookings from the database, cause:", exception);
            return Collections.emptySet();
        }
    }
}
