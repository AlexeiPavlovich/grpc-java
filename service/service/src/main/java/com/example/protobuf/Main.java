package com.example.protobuf;

import java.io.IOException;

import com.example.service.BankService;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {
		Server server=ServerBuilder.forPort(6565).addService(new BankService()).build();
		server.start();
		System.out.println("listening at 6565");
		server.awaitTermination();
		
	}
}
