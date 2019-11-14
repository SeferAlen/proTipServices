package com.protip.proTipServices.utility;

/**
 * The enum containing status after new message posting action
 */
public enum MessageReceivedStatus {
    POSTED,
    POSTED_WITH_NEW_TOKEN,
    EXPIRED_PRO_TIP_VALIDITY,
    ERROR
}
