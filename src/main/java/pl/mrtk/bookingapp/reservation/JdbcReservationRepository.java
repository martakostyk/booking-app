package pl.mrtk.bookingapp.reservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final static Logger log = LoggerFactory.getLogger(JdbcReservationRepository.class);

    private final static String SQL_INSERT = "INSERT INTO reservations (name, date_time, creation_timestamp) VALUES (?, ?, ?)";

    @Override
    public void insert(ReservationData reservationData) {
        try (Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost/bookingapp", "admin", "admin-pass");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setString(1, reservationData.getName());
            preparedStatement.setObject(2, reservationData.getDateTime());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            log.warn("Cannot insert reservation into the database, cause:", exception);
        }
    }
}
