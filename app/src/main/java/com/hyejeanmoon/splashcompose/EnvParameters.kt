/*
 * Copyright (C) 2021 HyejeanMOON.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hyejeanmoon.splashcompose

/**
 * EnvParameters
 *
 * The class is contains all of parameters in the project.
 */
class EnvParameters {
    companion object {
        const val BASE_URL = "https://api.unsplash.com"
        const val CLIENT_ID = ""
        const val DB_NAME = "SplashCompose.db"
        const val SHARED_PREFERENCE_DATA_NAME = "SPLASH_COMPOSE"
        const val KEY_ORDER_BY = "SPLASH_COMPOSE_ORDER_BY"
        const val KEY_DISPLAY_RESOLUTION = "SPLASH_COMPOSE_DISPLAY_RESOLUTION"
        const val KEY_PHOTO_ID = "SPLASH_COMPOSE_PHOTO_ID"
        const val KEY_PHOTO_USER_NAME = "SPLASH_COMPOSE_USER_NAME"
    }
}