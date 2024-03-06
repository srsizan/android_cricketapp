package com.samiun.mycricket.model.fixturewithdetails

import retrofit2.Callback

data class FixtureWithDetails(
    var `data`: FixtureWithDetailsData?
) {
    fun enqueue(callback: Callback<FixtureWithDetailsData>) {

    }
}