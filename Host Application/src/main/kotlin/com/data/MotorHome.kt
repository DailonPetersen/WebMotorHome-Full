package com.data

import org.jetbrains.exposed.sql.Table

data class MotorHome(
    val id: Int? = null,
    val modelo: String,
    val descricao: String,
    val ano: Int,
    val montadora: String,
    val placa: String,
    val exposicao: Boolean,
    val avaliacao: Int? = null)
{
    lateinit var listImages: List<ByteArray>
}

object MotorHomes: Table("MotorHomes") {
    val id = integer("id").autoIncrement().uniqueIndex()
    val modelo = varchar("modelo", 100)
    val descricao = varchar("descricao", 1024)
    val ano = integer("ano")
    val montadora = varchar("montadora", 20)
    val placa = varchar("placa", 7)
    val exposicao = bool("exposicao")
    val avaliacao = integer("avaliacao").nullable()

    override val primaryKey = PrimaryKey(id)
}