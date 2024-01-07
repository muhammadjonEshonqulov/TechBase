package uz.tech.base.utils

class Constants {
    companion object {
        const val TABLE_NAME = "table_name"
        val BASE_URL: String
            get() {
//                lg("used BASE_URL")
                return "https://techniques-back.vercel.app/"
            }

        const val DATABASE_NAME = "DATABASE_NAME"

        const val full_url = "https://fcm.googleapis.com/fcm/send"

        const val CONTENT_TYPE = "application/json"
    }
}