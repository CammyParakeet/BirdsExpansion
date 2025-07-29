package com.glance.birds.nest.behavior

import com.glance.birds.nest.Nest

interface NestTickHandler {
    fun tick(nest: Nest)
    fun tickAsync(nest: Nest) {}
}