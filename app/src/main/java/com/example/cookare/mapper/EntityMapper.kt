package com.example.cookare.mapper

interface EntityMapper <Entity, Model> {
    fun mapToModel(entity: Entity): Model
    fun mapFromModel(model: Model): Entity
}