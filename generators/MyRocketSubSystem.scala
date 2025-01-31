package mychip

import freechips.rocketchip.subsystem._
import freechips.rocketchip.devices.tilelink._
import freechips.rocketchip.amba.axi4._
import freechips.rocketchip.tilelink._
import freechips.rocketchip.diplomacy._

trait MyRocketSubsystem extends BaseSubsystem {
  
  // UART attached to Periphery Bus
  val uart = LazyModule(new TLUART(peripheryBus.beatBytes))
  peripheryBus.toVariableWidthSlave(Some("uart")) { uart.node }
  
  // SPI attached to Periphery Bus
  val spi = LazyModule(new TLSPI(peripheryBus.beatBytes))
  peripheryBus.toVariableWidthSlave(Some("spi")) { spi.node }

  // GPIO attached to Periphery Bus
  val gpio = LazyModule(new TLGPIO(peripheryBus.beatBytes))
  peripheryBus.toVariableWidthSlave(Some("gpio")) { gpio.node }

  // Memory connected to Memory Bus
  val mem = LazyModule(new TLTestRAM(address = 0x80000000L, depth = 8192))
  memoryBus.toVariableWidthSlave(Some("mem")) { mem.node }
}

