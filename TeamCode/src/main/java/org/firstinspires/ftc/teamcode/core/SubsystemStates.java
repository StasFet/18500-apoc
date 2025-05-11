package org.firstinspires.ftc.teamcode.core;

public class SubsystemStates {
    public enum OuttakeState {
        IDLE,
    }

    public enum IntakeStates {
        IDLE,
        ACTIVATED,
        EXTENDING,
        EXTENDED,
        EXTENDED_AND_DOWN,
        SEARCHING_FOR_SAMPLE,
        EJECTING,
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
