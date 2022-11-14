package com.example.service;

import com.example.models.Balance;
import com.example.models.BalanceCheckRequest;
import com.example.models.BankServiceGrpc;
import com.example.models.Money;
import com.example.models.WithdrawRequest;

import io.grpc.stub.StreamObserver;


public class BankService extends BankServiceGrpc.BankServiceImplBase {

	@Override
	public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {

		System.out.println(request);
		Balance balance = Balance.newBuilder().setAmount(request.getAccountNumber()).build();
		responseObserver.onNext(balance);
		responseObserver.onCompleted();
	}

	@Override
	public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
		System.out.println(request);
		if(request.getAccountNumbrt()==4) {
			responseObserver.onError(io.grpc.Status.FAILED_PRECONDITION.withDescription("haha").asException());
		}
		for(int i=1;i<10;i++) {
			responseObserver.onNext(Money.newBuilder().setValue(request.getAccountNumbrt()*i).build());
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		responseObserver.onCompleted();
	}
}
