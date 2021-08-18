package br.com.zup

import com.google.protobuf.Timestamp
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import java.time.LocalDateTime
import java.time.ZoneId

fun main() {
    
    val server = ServerBuilder
        .forPort(50051)
        .addService(FuncionarioEndpoint())
        .build()

    server.start()
    server.awaitTermination()
}

class FuncionarioEndpoint : Fase5grpcServiceGrpc.Fase5grpcServiceImplBase() {
    override fun cadastra(request: Fase5grpcRequest?, responseObserver: StreamObserver<Fase5grpcReply>?) {
        println(request!!)

        val instant = LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant()
        val criadoEm = Timestamp.newBuilder()
            .setSeconds(instant.epochSecond)
            .setNanos(instant.nano)
            .build()

        var nome = request?.name
        if (!request.hasField(Fase5grpcRequest.getDescriptor().findFieldByName("name"))) {
            nome = "[???]"
        }

        val response = Fase5grpcReply.newBuilder()
            .setMessage(nome)
            .setCriadoEm(criadoEm)
            .build()

        println(response)

        responseObserver?.onNext(response)
        responseObserver?.onCompleted()

    }
}
