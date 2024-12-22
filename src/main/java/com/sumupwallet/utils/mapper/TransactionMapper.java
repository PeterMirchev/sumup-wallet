package com.sumupwallet.utils.mapper;

import com.sumupwallet.utils.enums.TransactionType;
import com.sumupwallet.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionMapper {


    public static Transaction mapToTransaction(BigDecimal amount, TransactionType type) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

        OffsetDateTime now = OffsetDateTime.now();
        String formattedDate = now.format(dtf);

        LocalDateTime localDateTime = LocalDateTime.parse(formattedDate, dtf);

        return Transaction.builder()
                .type(type)
                .amount(amount)
                .createdOn(localDateTime)
                .build();
    }
}
