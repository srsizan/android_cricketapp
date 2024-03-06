package com.samiun.mycricket.model.fixturewithrun

data class FixtureWithRun(
    val `data`: List<FixtureWithRunEntity>,
    val links: Links?,
    val meta: Meta?
)