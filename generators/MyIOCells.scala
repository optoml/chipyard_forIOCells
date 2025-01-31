package myio

import chisel3._

class MyIOCell extends Module {
  val io = IO(new Bundle {
    val in = Input(UInt(1.W))
    val out = Output(WMy(UInt.1))
  })
  io.out := io.in
}

