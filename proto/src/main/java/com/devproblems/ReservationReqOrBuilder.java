// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: reservation.proto

package com.devProblems;

public interface ReservationReqOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.devProblems.ReservationReq)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.google.protobuf.Timestamp start_date = 1;</code>
   */
  boolean hasStartDate();
  /**
   * <code>.google.protobuf.Timestamp start_date = 1;</code>
   */
  com.google.protobuf.Timestamp getStartDate();
  /**
   * <code>.google.protobuf.Timestamp start_date = 1;</code>
   */
  com.google.protobuf.TimestampOrBuilder getStartDateOrBuilder();

  /**
   * <code>.google.protobuf.Timestamp end_date = 2;</code>
   */
  boolean hasEndDate();
  /**
   * <code>.google.protobuf.Timestamp end_date = 2;</code>
   */
  com.google.protobuf.Timestamp getEndDate();
  /**
   * <code>.google.protobuf.Timestamp end_date = 2;</code>
   */
  com.google.protobuf.TimestampOrBuilder getEndDateOrBuilder();

  /**
   * <code>int32 numberOfGuests = 3;</code>
   */
  int getNumberOfGuests();

  /**
   * <code>int32 accommodation_id = 4;</code>
   */
  int getAccommodationId();
}
