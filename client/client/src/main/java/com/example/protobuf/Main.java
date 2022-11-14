package com.example.protobuf;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import com.example.models.BankServiceGrpc;
import com.example.models.BankServiceGrpc.BankServiceBlockingStub;
import com.example.models.BankServiceGrpc.BankServiceStub;
import com.example.models.Money;
import com.example.models.WithdrawRequest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class Main {
	public static void main(String[] args) throws InterruptedException {

		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext().build();
		BankServiceBlockingStub blockingStub = BankServiceGrpc.newBlockingStub(channel);
		WithdrawRequest withdrawRequest=WithdrawRequest.newBuilder().setAccountNumbrt(1).build();
		//Balance balance=blockingStub.getBalance(BalanceCheckRequest.newBuilder().setAccountNumber(8).build());
		//blockingStub.withdraw(WithdrawRequest).forEachRemaining(System.out::println);
		//System.out.println(balance);
		
		BankServiceStub unblockingStub=BankServiceGrpc.newStub(channel);
		
		CountDownLatch latch=new CountDownLatch(1);
		unblockingStub.withdraw(withdrawRequest, new StreamObserver<Money>() {

			@Override
			public void onNext(Money value) {
				System.out.println(value);
				
			}

			@Override
			public void onError(Throwable t) {
				System.out.println("error");
				latch.countDown();
				
			}

			@Override
			public void onCompleted() {
				System.out.println("complete");
				latch.countDown();
				
			}
			
		});
		latch.await();
		System.out.println("finish");
		
	}
}
