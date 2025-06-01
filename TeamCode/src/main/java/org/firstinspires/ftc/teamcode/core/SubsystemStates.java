package org.firstinspires.ftc.teamcode.core;

public class SubsystemStates {
    public enum OuttakeState {
        IDLE,
        WAITING_FOR_TRANSFER,

    }

    public enum IntakeStates {
        IDLE,
        EXTENDING,
        EXTENDED,
        EXTENDED_AND_DOWN,
        ACTIVATED,
        HOLDING_SAMPLE,
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
