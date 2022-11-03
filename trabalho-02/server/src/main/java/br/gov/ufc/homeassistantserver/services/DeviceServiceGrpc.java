package br.gov.ufc.homeassistantserver.services;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.50.0)",
    comments = "Source: iot.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class DeviceServiceGrpc {

  private DeviceServiceGrpc() {}

  public static final String SERVICE_NAME = "DeviceService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<br.gov.ufc.homeassistantserver.services.Iot.Empty,
      br.gov.ufc.homeassistantserver.services.Iot.Empty> getShutdownDeviceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ShutdownDevice",
      requestType = br.gov.ufc.homeassistantserver.services.Iot.Empty.class,
      responseType = br.gov.ufc.homeassistantserver.services.Iot.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.gov.ufc.homeassistantserver.services.Iot.Empty,
      br.gov.ufc.homeassistantserver.services.Iot.Empty> getShutdownDeviceMethod() {
    io.grpc.MethodDescriptor<br.gov.ufc.homeassistantserver.services.Iot.Empty, br.gov.ufc.homeassistantserver.services.Iot.Empty> getShutdownDeviceMethod;
    if ((getShutdownDeviceMethod = DeviceServiceGrpc.getShutdownDeviceMethod) == null) {
      synchronized (DeviceServiceGrpc.class) {
        if ((getShutdownDeviceMethod = DeviceServiceGrpc.getShutdownDeviceMethod) == null) {
          DeviceServiceGrpc.getShutdownDeviceMethod = getShutdownDeviceMethod =
              io.grpc.MethodDescriptor.<br.gov.ufc.homeassistantserver.services.Iot.Empty, br.gov.ufc.homeassistantserver.services.Iot.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ShutdownDevice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.gov.ufc.homeassistantserver.services.Iot.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.gov.ufc.homeassistantserver.services.Iot.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new DeviceServiceMethodDescriptorSupplier("ShutdownDevice"))
              .build();
        }
      }
    }
    return getShutdownDeviceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorRequest,
      br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorResponse> getToggleActuatorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ToggleActuator",
      requestType = br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorRequest.class,
      responseType = br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorRequest,
      br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorResponse> getToggleActuatorMethod() {
    io.grpc.MethodDescriptor<br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorRequest, br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorResponse> getToggleActuatorMethod;
    if ((getToggleActuatorMethod = DeviceServiceGrpc.getToggleActuatorMethod) == null) {
      synchronized (DeviceServiceGrpc.class) {
        if ((getToggleActuatorMethod = DeviceServiceGrpc.getToggleActuatorMethod) == null) {
          DeviceServiceGrpc.getToggleActuatorMethod = getToggleActuatorMethod =
              io.grpc.MethodDescriptor.<br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorRequest, br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ToggleActuator"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DeviceServiceMethodDescriptorSupplier("ToggleActuator"))
              .build();
        }
      }
    }
    return getToggleActuatorMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DeviceServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DeviceServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DeviceServiceStub>() {
        @java.lang.Override
        public DeviceServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DeviceServiceStub(channel, callOptions);
        }
      };
    return DeviceServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DeviceServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DeviceServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DeviceServiceBlockingStub>() {
        @java.lang.Override
        public DeviceServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DeviceServiceBlockingStub(channel, callOptions);
        }
      };
    return DeviceServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DeviceServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DeviceServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DeviceServiceFutureStub>() {
        @java.lang.Override
        public DeviceServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DeviceServiceFutureStub(channel, callOptions);
        }
      };
    return DeviceServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class DeviceServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void shutdownDevice(br.gov.ufc.homeassistantserver.services.Iot.Empty request,
        io.grpc.stub.StreamObserver<br.gov.ufc.homeassistantserver.services.Iot.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getShutdownDeviceMethod(), responseObserver);
    }

    /**
     */
    public void toggleActuator(br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorRequest request,
        io.grpc.stub.StreamObserver<br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getToggleActuatorMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getShutdownDeviceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                br.gov.ufc.homeassistantserver.services.Iot.Empty,
                br.gov.ufc.homeassistantserver.services.Iot.Empty>(
                  this, METHODID_SHUTDOWN_DEVICE)))
          .addMethod(
            getToggleActuatorMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorRequest,
                br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorResponse>(
                  this, METHODID_TOGGLE_ACTUATOR)))
          .build();
    }
  }

  /**
   */
  public static final class DeviceServiceStub extends io.grpc.stub.AbstractAsyncStub<DeviceServiceStub> {
    private DeviceServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DeviceServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DeviceServiceStub(channel, callOptions);
    }

    /**
     */
    public void shutdownDevice(br.gov.ufc.homeassistantserver.services.Iot.Empty request,
        io.grpc.stub.StreamObserver<br.gov.ufc.homeassistantserver.services.Iot.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getShutdownDeviceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void toggleActuator(br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorRequest request,
        io.grpc.stub.StreamObserver<br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getToggleActuatorMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DeviceServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<DeviceServiceBlockingStub> {
    private DeviceServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DeviceServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DeviceServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public br.gov.ufc.homeassistantserver.services.Iot.Empty shutdownDevice(br.gov.ufc.homeassistantserver.services.Iot.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getShutdownDeviceMethod(), getCallOptions(), request);
    }

    /**
     */
    public br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorResponse toggleActuator(br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getToggleActuatorMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DeviceServiceFutureStub extends io.grpc.stub.AbstractFutureStub<DeviceServiceFutureStub> {
    private DeviceServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DeviceServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DeviceServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.gov.ufc.homeassistantserver.services.Iot.Empty> shutdownDevice(
        br.gov.ufc.homeassistantserver.services.Iot.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getShutdownDeviceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorResponse> toggleActuator(
        br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getToggleActuatorMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SHUTDOWN_DEVICE = 0;
  private static final int METHODID_TOGGLE_ACTUATOR = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DeviceServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DeviceServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SHUTDOWN_DEVICE:
          serviceImpl.shutdownDevice((br.gov.ufc.homeassistantserver.services.Iot.Empty) request,
              (io.grpc.stub.StreamObserver<br.gov.ufc.homeassistantserver.services.Iot.Empty>) responseObserver);
          break;
        case METHODID_TOGGLE_ACTUATOR:
          serviceImpl.toggleActuator((br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorRequest) request,
              (io.grpc.stub.StreamObserver<br.gov.ufc.homeassistantserver.services.Iot.ToggleActuatorResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class DeviceServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DeviceServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return br.gov.ufc.homeassistantserver.services.Iot.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DeviceService");
    }
  }

  private static final class DeviceServiceFileDescriptorSupplier
      extends DeviceServiceBaseDescriptorSupplier {
    DeviceServiceFileDescriptorSupplier() {}
  }

  private static final class DeviceServiceMethodDescriptorSupplier
      extends DeviceServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DeviceServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DeviceServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DeviceServiceFileDescriptorSupplier())
              .addMethod(getShutdownDeviceMethod())
              .addMethod(getToggleActuatorMethod())
              .build();
        }
      }
    }
    return result;
  }
}
