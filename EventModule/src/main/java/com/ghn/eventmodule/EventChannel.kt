package com.ghn.eventmodule

import kotlinx.coroutines.flow.Flow


/**
 * @author 浩楠
 * @date 2025/6/16
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 *  描述: TODO 提供统一的发送和订阅接口
 */
object EventChannel {

    fun <T : Any> post(event: T) {
        SharedFlowEventBus.post(event)
    }

    inline fun <reified T : Any> observe(sticky: Boolean = false): Flow<T> {
        return SharedFlowEventBus.observe(sticky)
    }

    inline fun <reified T : Any> observeOnlySticky(): Flow<T> {
        return SharedFlowEventBus.observeOnlySticky()
    }

    inline fun <reified T : Any> getStickyEvents(): List<Pair<T, Long>> {
        return SharedFlowEventBus.getStickyEvents()
    }

    inline fun <reified T : Any> setMaxStickyCacheSize(size: Int) {
        SharedFlowEventBus.setMaxStickyCacheSize<T>(size)
    }

    inline fun <reified T : Any> clearStickyEvents() {
        SharedFlowEventBus.clearStickyEvents<T>()
    }

    fun clearAllStickyEvents() {
        SharedFlowEventBus.clearAllStickyEvents()
    }
}