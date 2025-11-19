package com.buspass.queries;

import java.util.List;
import java.util.Map;

import com.buspass.db.QueryExecutionModule;

public class PaymentQuery {

    /**
     * Lists all Payments that the User has made
     * @param userId the UserID of the User who has made payments
     * @return the list of Payments
     */
    public List<Map<String, Object>> getPaymentsByUserId(int userId) {
        String sql = "SELECT PaymentID, Amount, PaymentDate, Method " +
                     "FROM Payment p LEFT JOIN Payment_Methods pm ON p.Payment_Method_ID = pm.Payment_Method_ID " +
                     "WHERE UserID = ?";
        
        List<Map<String, Object>> payments = QueryExecutionModule.executeQuery(sql, userId);
		return payments;  
    }

    //#region ADMIN PRIVILEDGES
    
    /**
     * Create a new transaction at this moment
     * @param userId the UserID of User table
     * @param amount amount of money paid in VND
     * @param paymentMethodId Payment_Method_ID of Payment_Methods table {1: Cash, 2: Card, 3: Online Banking}
     * @return whether the query is valid
     */
    public boolean createTransactionNow(int userId, float amount, int paymentMethodId) {
        String sql = "INSERT INTO Payment (UserID, Amount, PaymentDate, Payment_Method_ID) VALUES (?, ?, NOW(), ?);";
		int rowsAffected = QueryExecutionModule.executeUpdate(sql, userId, amount, paymentMethodId);
		return rowsAffected > 0;
    }

    public Map<String, Object> getPaymentById(int paymentId) {
        String sql = "SELECT p.Amount,\r\n" +
                     "   p.PaymentDate AS TransactionTime,\r\n" +
                     "   u.FullName,\r\n" +
                     "   pm.Method AS PaymentMethod\r\n" +
                     "FROM Payment p\r\n" +
                     "   LEFT JOIN `User` u ON p.UserID = u.UserID\r\n" +
                     "   LEFT JOIN Payment_Methods pm ON p.Payment_Method_ID = pm.Payment_Method_ID\r\n" +
                     "WHERE p.PaymentID = ?;";

        List<Map<String, Object>> payments = QueryExecutionModule.executeQuery(sql, paymentId);
        if (!payments.isEmpty()) {
            return payments.get(0);
        }
        return null;
    }

    public boolean deletePayment(int paymentId) {
        String sql = "DELETE FROM Payment WHERE PaymentID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, paymentId);
        return rowsAffected > 0;
    }

    //#endregion

}
