package pl.mrtk.bookingapp.reservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcReservationRepository.class);

    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DATE_TIME = "date_time";
    private static final String COLUMN_CREATION_TIMESTAMP = "creation_timestamp";

    private final static String SQL_QUERY_ALL = "SELECT * FROM reservations";
    private final static String SQL_INSERT = String.format("INSERT INTO reservations (%s, %s, %s) VALUES (?, ?, ?)",
            COLUMN_NAME, COLUMN_DATE_TIME, COLUMN_CREATION_TIMESTAMP);

    private final DataSource dataSource;

    public JdbcReservationRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ReservationData reservationData) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setString(1, reservationData.getName());
            preparedStatement.setObject(2, reservationData.getDateTime());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.warn("Cannot add reservation into the database, cause:", exception);
        }
    }

    @Override
    public Set<ReservationDto> getAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_QUERY_ALL)) {
            Set<ReservationDto> reservations = new HashSet<>();
            while (resultSet.next()) {
                ReservationDto reservation = ImmutableReservationDto.builder()
                        .name(resultSet.getString(COLUMN_NAME))
                        .dateTime((LocalDateTime) resultSet.getObject(COLUMN_DATE_TIME))
                        .creationTimestamp(resultSet.getTimestamp(COLUMN_CREATION_TIMESTAMP).toLocalDateTime())
                        .build();
                reservations.add(reservation);
            }
            return reservations;
        } catch (SQLException exception) {
            log.warn("Cannot get reservations from the database, cause:", exception);
            return Collections.emptySet();
        }
    }
}
