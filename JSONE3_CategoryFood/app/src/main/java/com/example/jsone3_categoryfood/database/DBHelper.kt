package com.example.jsone3_categoryfood.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.jsone3_categoryfood.models.Product
import com.example.jsone3_categoryfood.models.CartItemsResponse
import java.lang.Exception

class DBHelper(var mContext: Context):
    SQLiteOpenHelper(mContext,DATABASE_NAME,null,VERSION) {

    companion object {
        const val DATABASE_NAME = "grocery"
        const val VERSION = 1
        const val TABLE_NAME = "products"
        const val COLUMN_ID = "id"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_NAME = "name"
        const val COLUMN_PRICE = "price"
        const val COLUMN_MRP = "mrp"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_DISCOUNT = "discount"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var createTable =
            "create table $TABLE_NAME ($COLUMN_ID varchar(50), $COLUMN_IMAGE varchar(30), $COLUMN_NAME char(20), $COLUMN_PRICE float, $COLUMN_MRP float, $COLUMN_QUANTITY int, $COLUMN_DISCOUNT float)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        var dropTable = "drop table $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    fun checkDuplicate(id: String): Int {
        var db = readableDatabase
        var columns = arrayOf(
            COLUMN_ID,
            COLUMN_QUANTITY
        )
        var cursor =
            db.query(TABLE_NAME, columns, null, null, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            do {
                var checkedId = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                if (checkedId == id)
                    return cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
            } while (cursor.moveToNext())
            Toast.makeText(mContext, "${cursor.count.toString()} records found", Toast.LENGTH_SHORT)
                .show()
        }
        return 0
        cursor.close()
        db.close()
    }

    fun addProduct(product:Product){
        var quantity = checkDuplicate(product._id)

        if(quantity == 0){
            var db = writableDatabase
            var contentValues = ContentValues()
            contentValues.put(COLUMN_ID,product._id)
            contentValues.put(COLUMN_NAME,product.productName)
            contentValues.put(COLUMN_IMAGE,product.image)
            contentValues.put(COLUMN_MRP,product.mrp)
            contentValues.put(COLUMN_PRICE,product.price)
            contentValues.put(COLUMN_QUANTITY,1)

            db.insert(TABLE_NAME,null, contentValues)
        }else{
            increaseQuantity(product._id,quantity)
        }
    }

    fun readProduct():ArrayList<CartItemsResponse> {
        var cartList: ArrayList<CartItemsResponse> = ArrayList()
        var db = readableDatabase
        var columns = arrayOf(
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_IMAGE,
            COLUMN_MRP,
            COLUMN_PRICE,
            COLUMN_QUANTITY
        )

        var cursor =
            db.query(TABLE_NAME,columns,null,null,null,null,null)

        if(cursor != null && cursor.moveToFirst()){
            do{
                var id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                var name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                var image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                var mrp = cursor.getFloat(cursor.getColumnIndex(COLUMN_MRP))
                var price= cursor.getFloat(cursor.getColumnIndex(COLUMN_PRICE))
                var quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))

                var cartItems = CartItemsResponse(id,name,image,mrp,price,quantity)
                cartList.add(cartItems)

            }while(cursor.moveToNext())
            Toast.makeText(mContext,"${cursor.count.toString()} records found", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return cartList
    }

    fun deleteProduct(id: String):Boolean{
        var db = writableDatabase
        var whereClause = "$COLUMN_ID = ?"
        var whereArgs = arrayOf(id.toString())
        var result: Boolean = false
        try{
            db.delete(TABLE_NAME,whereClause,whereArgs)
            result = true
        }catch (e : Exception){
            Log.e(ContentValues.TAG, "Error deleting")
        }
        db.close()
        return result
    }

    fun deleteAll():Boolean{
        var db = writableDatabase
        var result: Boolean = false
        try{
            db.delete(TABLE_NAME,null,null)
            result = true
        }catch (e : Exception){
            Log.e(ContentValues.TAG, "Error deleting")
        }
        db.close()
        return result
    }

    fun increaseQuantity(id: String, quantity: Int){
        var db = writableDatabase
        var whereClause = "$COLUMN_ID = ?"
        var whereArgs = arrayOf(id)
        var contenValue = ContentValues()
        contenValue.put(COLUMN_QUANTITY,quantity+1)
        db.update(TABLE_NAME,contenValue,whereClause,whereArgs)
    }

    fun decreaseQuantity(id: String, quantity: Int) {
        if (quantity - 1 == 0) {
            deleteProduct(id)
        } else {
            var db = writableDatabase
            var whereClause = "$COLUMN_ID = ?"
            var whereArgs = arrayOf(id)
            var contenValue = ContentValues()
            contenValue.put(COLUMN_QUANTITY, quantity - 1)
            db.update(TABLE_NAME, contenValue, whereClause, whereArgs)
        }
    }
}