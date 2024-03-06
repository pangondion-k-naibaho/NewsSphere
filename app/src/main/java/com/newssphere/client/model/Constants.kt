package com.newssphere.client.model

class Constants {

    interface URL_CONSTANTS{
        companion object{
            const val NEWSAPI_URL = "https://newsapi.org/v2/"
        }
    }

    interface KEY{
        companion object{
            const val NEWSAPI_KEY = "8b80900abff0438d90b3c7a86f31afe4"
        }
    }

    interface COUNTRY_CONSTANTS{
        companion object{
            const val AE = "ae"
            const val AR = "ar"
            const val AT = "at"
            const val AU = "au"
            const val BE = "be"
            const val BG = "bg"
            const val BR = "br"
            const val CA = "ca"
            const val CH = "ch"
            const val CN = "cn"
            const val CO = "co"
            const val CU = "cu"
            const val CZ = "cz"
            const val DE = "de"
            const val EG = "eg"
            const val FR = "fr"
            const val GB = "gb"
            const val GR = "gr"
            const val ID = "id"
            const val US = "us"
        }

        interface CATEGORY_CONSTANTS{
            companion object{
                const val ALL = "all"
                const val BUSINESS = "business"
                const val ENTERTAINMENT = "entertainment"
                const val GENERAL = "general"
                const val HEALTH = "health"
                const val SCIENCE = "science"
                const val SPORTS = "sports"
                const val TECHNOLOGY = "technology"

                enum class CATEGORY_ENUM{
                    All, Business, Entertainment, General, Health, Science, Sports, Technology
                }
            }
        }
    }

}