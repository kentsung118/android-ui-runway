package com.kent.android.slim.sample.pratice.coupon

/**
 * Created by songzhukai on 2021/1/7.
 */
sealed class Coupon2 {
    companion object {
        val CashType = "CASH"
        val DiscountType = "DISCOUNT"
        val GiftType = "GIFT"
    }

    class CashCoupon(val id: Long, val type: String, val leastCost: Long, val reduceCost: Long) : Coupon2()
    class DiscountCoupon(val id: Long, val type: String, val discount: Int) : Coupon2()
    class GiftCoupon(val id: Long, val type: String, val gift: String) : Coupon2()
}

sealed class CouponStatus {
    data class StatusNotFetched(val coupon: Coupon2) : CouponStatus()
    data class StatusFetched(val coupon: Coupon2, val user: User2) : CouponStatus()
    data class StatusUsed(val coupon: Coupon2, val user: User2) : CouponStatus()
    data class StatusExpired(val coupon: Coupon2) : CouponStatus()
    data class StatusUnAvilable(val coupon: Coupon2) : CouponStatus()
}


class User2

fun fetched(c: Coupon2, user: User2): Boolean {
    return false
}

fun used(c: Coupon2, user: User2): Boolean {
    return true
}

fun isExpired(c: Coupon2): Boolean {
    return false
}

fun isUnAvilable(c: Coupon2): Boolean {
    return false
}

fun getCouponStatus(coupon: Coupon2, user: User2): CouponStatus = when {
    isUnAvilable(coupon) -> CouponStatus.StatusUnAvilable(coupon)
    isExpired(coupon) -> CouponStatus.StatusExpired(coupon)
    used(coupon, user) -> CouponStatus.StatusUsed(coupon, user)
    fetched(coupon, user) -> CouponStatus.StatusFetched(coupon, user)
    else -> CouponStatus.StatusNotFetched(coupon)
}

fun showStatus2(coupon: Coupon2, user: User2) = when (getCouponStatus(coupon, user)) {
    is CouponStatus.StatusUnAvilable -> showUnAvilable2()
    is CouponStatus.StatusExpired -> showExpired2()
    is CouponStatus.StatusFetched -> showFetched2()
    is CouponStatus.StatusUsed -> showUsed2()
    is CouponStatus.StatusNotFetched -> showNotFetched2()
}

fun showUsed2() {}
fun showFetched2() {}
fun showExpired2() {}
fun showNotFetched2() {}
fun showUnAvilable2() {}

fun main() {
    val coupon = Coupon2.GiftCoupon(1111L, Coupon2.GiftType, "gift")
    showStatus2(coupon, User2())
}