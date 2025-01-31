package mychip

import chisel3._
import freechips.rocketchip.subsystem._
import freechips.rocketchip.tile._
import freechips.rocketchip.devices.tilelink._
import freechips.rocketchip.amba.axi4._
import freechips.rocketchip.prci._

class MyRocketConfig extends Config((site, here, up) => {
  case RocketTilesKey => Seq(RocketTileParams(
    core = RocketCoreParams(
      useVM = true,  // Enable virtual memory
      mulDiv = Some(MulDivParams()), // Enable multiplication and division
      fpu = Some(FPUParams()) // Enable FPU
    ),
    btb = None, 
    dcache = Some(DCacheParams()), 
    icache = Some(ICacheParams()),
    hartId = 0
  ))

  case SystemBusKey => SystemBusParams(beatBytes = 8)
  case PeripheryBusKey => PeripheryBusParams(beatBytes = 8)
  case MemoryBusKey => MemoryBusParams(beatBytes = 8)
})

