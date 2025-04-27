package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import dev.frozenmilk.dairy.pasteurized.SDKGamepad
import dev.frozenmilk.mercurial.Mercurial
import dev.frozenmilk.mercurial.bindings.BoundGamepad
import dev.frozenmilk.mercurial.commands.Lambda
import dev.frozenmilk.mercurial.commands.util.StateMachine
import dev.frozenmilk.util.cell.RefCell
import org.firstinspires.ftc.teamcode.commands.Drive
import org.firstinspires.ftc.teamcode.dairy.subsystems.*
import org.firstinspires.ftc.teamcode.state.*
import org.firstinspires.ftc.teamcode.state.SubsystemStates.IntakeStates
import org.firstinspires.ftc.teamcode.state.SubsystemStates.OuttakeState

@Mercurial.Attach
@DriveTrain.Attach
@TeleOp(name = "Mercurial TeleOp")
class MercTele : OpMode() {
    override fun init() {

        val boundGamepad = BoundGamepad(SDKGamepad(gamepad1))
        DriveTrain.rcDrive()

        val intakeStateMachine = StateMachine(SubsystemStates.IntakeStates.IDLE)
            .withState(IntakeStates.IDLE) { state: RefCell<IntakeStates>, name:String ->
                Lambda("filler")    // mini state machine from IDLE to EXTENDED
                    // this will be intake extend and ready
                    .addEnd {
                        state.accept(IntakeStates.EXTENDED)
                    }
            }
            .withState(IntakeStates.EXTENDED) { state: RefCell<IntakeStates>, name:String ->
                Lambda("filler")
                    // this will be detect sample and auto retract
                    .addEnd {
                        state.accept(IntakeStates.TRANSFER)
                    }
            }
            .withState(IntakeStates.TRANSFER) { state: RefCell<IntakeStates>, name:String ->
                Lambda("filler")
                    // this will be stay still pretty much
                    .addEnd {
                        state.accept(IntakeStates.IDLE)
                    }
            }
    }

    override fun loop() {

    }
}