package com.riskycase.corereview.data

import android.R.attr.data
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


private const val myDatabaseVersion : Int = 1
private const val myDatabaseName : String = "productsDatabase"

class ProductPersistence(
    context: Context
) :
    SQLiteOpenHelper(context, myDatabaseName, null, myDatabaseVersion) {
    private val myTableName : String = "products"
    private val keyId : String = "id"
    private val keyProduct : String = "product"
    private val keyCount : String = "count"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $myTableName ($keyId INTEGER PRIMARY KEY, $keyProduct BLOB, $keyCount INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $myTableName")
        onCreate(db)
    }

    fun getAllProducts() : List<Product> {
        val result = ArrayList<Product>()
        val db = this.readableDatabase
        val cursor = db.query(
            myTableName,
            arrayOf(keyId, keyProduct, keyCount),
            null,
            null,
            null,
            null,
            "$keyId ASC"
        )
        if(cursor.moveToFirst()){
            do {
                result.addAll(List<Product>(cursor.getInt(2)) {
                    val baip = ByteArrayInputStream(cursor.getBlob(1))
                    val ois = ObjectInputStream(baip)
                    return@List  ois.readObject() as Product
                })
            } while (cursor.moveToNext())
        }
        db.close()
        return result.toList()
    }

    fun getCount() : Int {
        var result : Int = 0
        val db = this.readableDatabase
        val cursor = db.query(
            myTableName,
            arrayOf(keyCount),
            null,
            null,
            null,
            null,
            "$keyId ASC"
        )
        if(cursor.moveToFirst()){
            do {
                result += cursor.getInt(0)
            } while (cursor.moveToNext())
        }
        db.close()
        return result
    }

    fun addProduct(product: Product) {
        val db = this.writableDatabase
        val cursor = db.query(
            myTableName,
            arrayOf(keyCount),
            null,
            null,
            null,
            null,
            "$keyId ASC"
        )
        val values = ContentValues()
        if(cursor.moveToFirst()) {
            values.put(keyCount, cursor.getInt(0) + 1)
            db.update(myTableName, values, "$keyId =?",  arrayOf(product.id.toString()))
        }
        else {

            val baos = ByteArrayOutputStream()
            val oos = ObjectOutputStream(baos)
            oos.writeObject(product)

            values.put(keyId, product.id)
            values.put(keyProduct, baos.toByteArray())
            values.put(keyCount, 1)

            db.insert(myTableName, null, values)
        }
        db.close()
    }

}