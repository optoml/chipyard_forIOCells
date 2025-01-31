package chipyard.iobinders

import chisel3._
import chisel3.experimental.{Analog, IO, DataMirror}

import org.chipsalliance.cde.config._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.devices.debug._
import freechips.rocketchip.jtag.{JTAGIO}
import freechips.rocketchip.subsystem._
import freechips.rocketchip.system.{SimAXIMem}
import freechips.rocketchip.amba.axi4.{AXI4Bundle, AXI4SlaveNode, AXI4MasterNode, AXI4EdgeParameters}
import freechips.rocketchip.util._
import freechips.rocketchip.prci._
import freechips.rocketchip.groundtest.{GroundTestSubsystemModuleImp, GroundTestSubsystem}
import freechips.rocketchip.tilelink.{TLBundle}

import sifive.blocks.devices.gpio._
import sifive.blocks.devices.uart._
import sifive.blocks.devices.spi._
import tracegen.{TraceGenSystemModuleImp}

import barstools.iocell.chisel._

import testchipip._
import icenet.{CanHavePeripheryIceNIC, SimNetwork, NicLoopback, NICKey, NICIOvonly}
import chipyard.{CanHaveMasterTLMemPort}
import chipyard.clocking.{HasChipyardPRCI, DividerOnlyClockGenerator}

import scala.reflect.{ClassTag}

class WithGPIOIOCells extends OverrideIOBinder({
  (system: HasPeripheryGPIOModuleImp) => {
    val (ports2d, cells2d) = system.gpio.zipWithIndex.map { case (gpio, i) =>
      gpio.pins.zipWithIndex.map { case (pin, j) =>
        val g = IO(Analog(1.W)).suggestName(s"gpio_${i}_${j}")
        val iocell = system.p(IOCellKey).gpio().suggestName(s"iocell_gpio_${i}_${j}")
        iocell.io.o := pin.o.oval
        iocell.io.oe := pin.o.oe
        iocell.io.ie := pin.o.ie
        pin.i.ival := iocell.io.i
        iocell.io.pad <> g
        (g, iocell)
      }.unzip
    }.unzip
    val ports: Seq[Analog] = ports2d.flatten
    (ports, cells2d.flatten)
  }
})
///////////////////////////////////////////////////////////////////////////////////////////////////
//IOBinders changed code
//////////////////////////////////////////////////////////////////////////////////////////////////
/*
package mychip

import chisel3._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.util._
import freechips.rocketchip.subsystem._
import freechips.rocketchip.devices.tilelink._
import freechips.rocketchip.tile._
import freechips.rocketchip.amba.axi4._
import freechips.rocketchip.prci._
import barstools.iocell.chisel._

object MyIOBinders {

  // Helper function to wrap an IO with IOCells
  def attachIOCells(io: Data): Data = {
    io match {
      case bundle: Bundle =>
        val ioCellWrapper = IO(bundle.cloneType)
        ioCellWrapper.suggestName(bundle.instanceName + "_iocell")
        ioCellWrapper <> bundle
        ioCellWrapper
      case _ => io
    }
  }

  // Binding Debug, UART, SPI, GPIO to IOCells
  def myCustomIOBinder: PartialFunction[(HasChipyardSubsystem, Annotated[Data]), Unit] = {
    case (dut: HasChipyardSubsystem, io: Annotated[Data]) =>
      io match {
        case Annotated(_, debug: DebugIO) =>
          debug.clock := attachIOCells(debug.clock)
          debug.reset := attachIOCells(debug.reset)
          debug.dmi <> attachIOCells(debug.dmi)

        case Annotated(_, uart: UARTPortIO) =>
          uart.txd := attachIOCells(uart.txd)
          uart.rxd := attachIOCells(uart.rxd)

        case Annotated(_, spi: SPIPortIO) =>
          spi.sck := attachIOCells(spi.sck)
          spi.cs.foreach { cs => cs := attachIOCells(cs) }
          spi.mosi.foreach { mosi => mosi := attachIOCells(mosi) }
          spi.miso.foreach { miso => miso := attachIOCells(miso) }

        case Annotated(_, gpio: GPIOPortIO) =>
          gpio.pins.foreach { pin => pin.o := attachIOCells(pin.o) }
      }
  }
}

*/
/////////////////////////////////////
//simple IOBinder
///////////////////////////////////
/*package myio

import chipyard.iobinders.IOBinder
import freechips.rocketchip.diplomacy._
import chisel3._

object MyIOBinder extends IOBinder {
  override def apply[T <: LazyModule](system: T): Seq[Module] = {
    val myCell = Module(new MyIOCell)
    system.module.clock := myCell.clock
    system.module.reset := myCell.reset
    Seq(myCell)
  }
}
*/
