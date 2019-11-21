package com.protip.proTipServices.utility;

/**
 * The enum containing status after actions allowed to perform only ProTipUser account
 */
public enum ProTipUserActionStatus {
    ADMIN,
    OK,
    OK_WITH_NEW_TOKEN,
    EXPIRED_PRO_TIP_VALIDITY,
    ERROR
}
