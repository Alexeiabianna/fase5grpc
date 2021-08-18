package br.com.zup

import java.io.FileInputStream
import java.io.FileOutputStream

fun main() {

    val request = Fase5grpcRequest.newBuilder()
        .setName("Alexei")
        .setCpf("02814459911")
        .setIdade(28)
        .setSalario(5000.00)
        .setAtivo(true)
        .setCargo(Cargo.DEV)
        .addEnderecos(Endereco.newBuilder()
            .setLogradouro("Rua da Gruta")
            .setCep("91720160")
            .setComplemento("AP 1000")
            .build()
        )
        .build()

    println(request)
    request.writeTo(FileOutputStream("funcionario-request.bin"))

    val r2 = Fase5grpcRequest.newBuilder().mergeFrom(FileInputStream("funcionario-request.bin"))

    r2.setCargo(Cargo.GERENTE).build()

    println(r2)
}