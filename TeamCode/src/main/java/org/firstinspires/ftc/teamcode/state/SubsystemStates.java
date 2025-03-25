package org.firstinspires.ftc.teamcode.state;

public class SubsystemStates {
    public enum OuttakeState {
        IDLE,
    }

    public enum IntakeStates {
        IDLE,
        EXTENDING,
        SEARCHING_FOR_SAMPLE,
        RETRACTING,
        TRANSFER
    }

    public enum HangStates {
        DOWN,
        EXTENDING,
        EXTENDED,
        RETRACTING,
        RETRACTED
    }
}
