package flows.callbacking

interface DummyCallback {
    fun onUpdate(value: Int)
    fun onError(message: String?)
    fun onComplete()
}

