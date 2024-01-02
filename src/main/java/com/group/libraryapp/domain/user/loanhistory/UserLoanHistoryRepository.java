package com.group.libraryapp.domain.user.loanhistory;

import com.group.libraryapp.domain.user.loan_history.UserLoanHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory, Long> {

  UserLoanHistory findByBookNameAndIsReturn(String bookName, boolean isReturn);

}
