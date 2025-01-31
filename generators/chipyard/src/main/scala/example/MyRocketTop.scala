package mychip

import chisel3._
import freechips.rocketchip.system._

class MyRocketTop(implicit p: Parameters) extends RocketSubsystem with MyRocketSubsystem {
  override lazy val module = new MyRocketTopModule(this)
}

class MyRocketTopModule[+L <: MyRocketTop](l: L) extends RocketSubsystemModuleImp(l) {
  // Attach IOCells to all external interfaces
  val io_debug = IO(new DebugIO())
  io_debug <> l.debug
  
  val io_uart = IO(new UARTPortIO)
  io_uart <> l.uart.module.io.port
  
  val io_spi = IO(new SPIPortIO)
  io_spi <> l.spi.module.io.port
  
  val io_gpio = IO(new GPIOPortIO)
  io_gpio <> l.gpio.module.io.port
}

