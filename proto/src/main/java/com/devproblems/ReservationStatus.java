// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: reservation.proto

package com.devProblems;

/**
 * Protobuf enum {@code com.devProblems.ReservationStatus}
 */
public enum ReservationStatus
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>RESERVATION_STATUS_PENDING = 0;</code>
   */
  RESERVATION_STATUS_PENDING(0),
  /**
   * <code>RESERVATION_STATUS_ACCEPTED = 1;</code>
   */
  RESERVATION_STATUS_ACCEPTED(1),
  /**
   * <code>RESERVATION_STATUS_DECLINED = 2;</code>
   */
  RESERVATION_STATUS_DECLINED(2),
  /**
   * <code>RESERVATION_STATUS_DELETED = 3;</code>
   */
  RESERVATION_STATUS_DELETED(3),
  /**
   * <code>RESERVATION_STATUS_CANCELED = 4;</code>
   */
  RESERVATION_STATUS_CANCELED(4),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>RESERVATION_STATUS_PENDING = 0;</code>
   */
  public static final int RESERVATION_STATUS_PENDING_VALUE = 0;
  /**
   * <code>RESERVATION_STATUS_ACCEPTED = 1;</code>
   */
  public static final int RESERVATION_STATUS_ACCEPTED_VALUE = 1;
  /**
   * <code>RESERVATION_STATUS_DECLINED = 2;</code>
   */
  public static final int RESERVATION_STATUS_DECLINED_VALUE = 2;
  /**
   * <code>RESERVATION_STATUS_DELETED = 3;</code>
   */
  public static final int RESERVATION_STATUS_DELETED_VALUE = 3;
  /**
   * <code>RESERVATION_STATUS_CANCELED = 4;</code>
   */
  public static final int RESERVATION_STATUS_CANCELED_VALUE = 4;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static ReservationStatus valueOf(int value) {
    return forNumber(value);
  }

  public static ReservationStatus forNumber(int value) {
    switch (value) {
      case 0: return RESERVATION_STATUS_PENDING;
      case 1: return RESERVATION_STATUS_ACCEPTED;
      case 2: return RESERVATION_STATUS_DECLINED;
      case 3: return RESERVATION_STATUS_DELETED;
      case 4: return RESERVATION_STATUS_CANCELED;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<ReservationStatus>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      ReservationStatus> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<ReservationStatus>() {
          public ReservationStatus findValueByNumber(int number) {
            return ReservationStatus.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return com.devProblems.Reservation.getDescriptor().getEnumTypes().get(0);
  }

  private static final ReservationStatus[] VALUES = values();

  public static ReservationStatus valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private ReservationStatus(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:com.devProblems.ReservationStatus)
}

