package pl.mrtk.bookingapp.reservation;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    @Override
    public void add(ReservationData reservationData) {
        try ( Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost/bookingapp", "admin", "admin-pass");
             Statement statement = connection.createStatement()) {
            String insertSql = String.format(
                    "INSERT INTO reservations (name, date_time, creation_timestamp) VALUES ('%s', '%s', '%s')",
                    reservationData.getName(), reservationData.getDateTime(), Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate(insertSql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
