package com.example.echohealth.SoundCaluculator


fun SafeListeningduration(db: Double): String {
    return when {
        db <= 85 -> "Up to 8 hrs"
        db <= 90 -> "Up to 2 hrs"
        db <= 95 -> "Up to 1 hr"
        db<= 100 -> "Up to 15 min"
        else -> "Danger sound level! Avoid listening."
    }
}