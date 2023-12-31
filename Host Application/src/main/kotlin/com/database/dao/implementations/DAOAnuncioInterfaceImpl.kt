package com.database.dao.implementations

import com.data.Anuncio
import com.data.Anuncios
import com.database.dao.facades.DAOAnuncioInterface
import com.plugins.DatabaseUtil
import com.plugins.Databases
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOAnuncioInterfaceImpl : DAOAnuncioInterface {
    private val logger = KotlinLogging.logger("advertisementRepository")

    override suspend fun getAll(): List<Anuncio> = Databases.dbQuery {
        Anuncios.selectAll().map{ DatabaseUtil.resultRowToAnuncio(it) }
    }

    override suspend fun get(id: Int): Anuncio? = Databases.dbQuery {
        Anuncios.select { Anuncios.id eq id }
            .map{ DatabaseUtil.resultRowToAnuncio(it) }
            .singleOrNull()
    }

    override suspend fun insert(anuncio: Anuncio): Anuncio? = Databases.dbQuery {
        val statement = Anuncios.insert {
            it[idMotorhome] = anuncio.idMotorhome
            it[idCriador] = anuncio.idCriador
            it[precoAluguel] = anuncio.precoAluguel?.run { this } ?: 0.00
            it[precoVenda] = anuncio.precoVenda?.run { this } ?: 0.00
            it[descricao] = anuncio.descricao
            it[disponivelParaAluguel] = anuncio.disponivelParaAluguel?.run { this } ?: false
            it[dataDeDisponibilidadeAluguel] = anuncio.dataDeDisponibilidadeAluguel?.run { this } ?: 0
            it[dataDeDisponibilidadeExposicao] = anuncio.dataDeDisponibilidadeExposicao?.run { this } ?: 0
        }
        statement.resultedValues?.singleOrNull()?.let { DatabaseUtil.resultRowToAnuncio(it) }
    }

    override suspend fun update(anuncio: Anuncio): Boolean = Databases.dbQuery {
        Anuncios.update(where = { Anuncios.id eq anuncio.id as Int }) {
            it[idMotorhome] = anuncio.idMotorhome
            it[idCriador] = anuncio.idCriador
            it[precoAluguel] = anuncio.precoAluguel?.run { this } ?: 0.00
            it[precoVenda] = anuncio.precoVenda?.run { this } ?: 0.00
            it[descricao] = anuncio.descricao
            it[disponivelParaAluguel] = anuncio.disponivelParaAluguel?.run { this } ?: false
            it[dataDeDisponibilidadeAluguel] = anuncio.dataDeDisponibilidadeAluguel?.run { this } ?: 0
            it[dataDeDisponibilidadeExposicao] = anuncio.dataDeDisponibilidadeExposicao?.run { this } ?: 0
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = Databases.dbQuery {
        Anuncios.deleteWhere { Anuncios.id eq id } > 0
    }
}