package rift.elonautilities

class Magic {
    enum class Stat {WILL, MAGIC, PERCEPTION, CHARISMA, LEARNING, DEXTERITY, CONSTITUTION}
    enum class MBType {GUILD, DREAM, NORMAL}

    class MagicBook(val spellID:Short, val jpName:String, val spellName:String, val bookName:String, val itemID:Short,
            val stat:Stat, val mpgp:Short, val difficulty:Short) {

        val mbType: MBType = when {
            this.spellName.isBlank() -> MBType.GUILD
            this.bookName.isBlank() -> MBType.DREAM
            else -> MBType.NORMAL
        }

        fun title(): String = when (mbType) {
            MBType.GUILD -> "Ancient book titled <$bookName>"
            MBType.DREAM -> "Non-book spell: $spellName"
            MBType.NORMAL -> "Spellbook of $bookName"
        }

        fun describe(): String = when (mbType) {
            MBType.GUILD ->  ("""${title()}
                              |    Japanese name: $jpName
                              |    Item ID: $itemID
                              |    GP yield: $mpgp
                              |    Reading difficulty: $difficulty""".trimMargin())
            MBType.DREAM ->  ("""${title()}
                              |    Japanese name: $jpName
                              |    Spell ID: $spellID
                              |    Stat: $stat}
                              |    MP cost: $mpgp}
                              |    Casting difficulty: $difficulty""".trimMargin())
            MBType.NORMAL->  ("""${title()}
                              |    Japanese name: ${this.jpName}
                              |    Spell ID: $spellID
                              |    Spell name: $spellName
                              |    Item ID: $itemID
                              |    Stat: $stat
                              |    MP cost: $mpgp
                              |    Reading difficulty: $difficulty""".trimMargin())
        }
        override fun toString(): String = this.title()
    }


    fun statFromIcon(icon: String): Stat? = when(icon) {
        "[[File:Icon-will_plus.gif]]" -> Stat.WILL
        "[[File:icon-magic Plus.gif]]" -> Stat.MAGIC
        "[[File:icon-perception plus.gif]]" -> Stat.PERCEPTION
        "[[File:icon-charisma Plus.gif]]" -> Stat.CHARISMA
        "[[File:icon-learning plus.gif]]" -> Stat.LEARNING
        "[[File:icon-dexterity plus.gif]]" -> Stat.DEXTERITY
        "[[File:icon-constitution plus.gif]]" -> Stat.CONSTITUTION
        else -> null
    }

    var magicBooks = mutableListOf<MagicBook>()

    // list.filter {it.stat == search}
    // list.filter {it.diff <= search}
    // list.filter {a <= it.diff <= b}
    // list.filter {(a-b) <= it.diff <= (a+b)
    // list.filter {it.bookName.contains(search, ignoreCase = true)}
    // list.filter {it.spellName.contains(search, ignoreCase = true)}
    // list.filter { with(it){bookName.contains(search, ignoreCase = true)
    //                    || spellName.contains(search, ignoreCase = true)} }

    fun getDifficulty(exName: String): Short {
        val candidates = magicBooks.filter { with(it) {
          spellName.contains(exName, ignoreCase = true) ||
          bookName.contains(exName, ignoreCase = true)}}
        when(candidates.size){
            0 -> throw IllegalArgumentException("No books returned for given search name '$exName'")
            1 -> return candidates[0].difficulty
            else -> throw IllegalArgumentException("Too many books returned for search '$exName': " +
                "$candidates")
        }
    }


    enum class BookDescriptor {NONE, DIFFICULTY, MPGP}
    fun describeBooks(mbs: List<MagicBook>, add: BookDescriptor): String = when(mbs.size){
            0 -> "No books found"
            1 -> mbs[0].describe()// full description
            else -> {

                fun label(book: MagicBook): String = when(add) {
                        BookDescriptor.DIFFICULTY -> "${book.title()} (${book.difficulty})"
                        BookDescriptor.MPGP -> "${book.title()} (${book.mpgp})"
                        else -> book.title()
                }

                mbs.joinToString("\n", transform = ::label)

            }
    }

/*    fun main(args: Array<String>): Unit{
        //TODO: read in wiki-formatted table

    }*/

}