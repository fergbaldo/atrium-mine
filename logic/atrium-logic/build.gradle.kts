import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("build-logic.published-kotlin-multiplatform")
}

description = "The domain logic of Atrium"

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(prefixedProject("core"))
                // it is up to the consumer which atrium-translations module is used at runtime
                compileOnly(prefixedProject("translations-en_GB"))
            }
        }
        jvmMain {
            dependencies {
                implementation(libs.niok)
            }
        }

        commonTest {
            dependencies {
                implementation(prefixedProject("api-fluent"))
                implementation(prefixedProject("specs"))
            }
        }
        jvmTest {
            dependencies {
                implementation(prefixedProject("specs"))
            }
        }
    }
}

tasks.configureEach<AbstractDokkaLeafTask> {
    dokkaSourceSets.configureEach {
        reportUndocumented.set(false)
    }
}

// TODO 1.3.0 some bug in dokka, maybe fixed in a never version? We should be able to suppress jvmMain for js and common
// see https://github.com/Kotlin/dokka/issues/3209
project.afterEvaluate {
    tasks.named<DokkaTask>("dokkaHtmlJs") {
        dokkaSourceSets.named("jvmMain") {
            suppress.set(false)
        }
    }
    tasks.named<DokkaTask>("dokkaHtmlKotlinMultiplatform") {
        dokkaSourceSets.named("jvmMain") {
            suppress.set(false)
        }
    }
}


createGenerateLogicTask(
    includingTarget("jvm"),
    includingTarget("common",
        "/creating/charsequence/contains/creators" to { _ ->
            "<T : CharSequence, S : CharSequenceContains.SearchBehaviour> CharSequenceContains.CheckerStepLogic<T, S>" to
                "entryPointStepLogic.container.getImpl"
        },
        "/creating/iterable/contains/creators" to { path ->
            when (val fileNameAsString = path.fileName.toString()) {
                "IterableLikeContainsInAnyOrderAssertions.kt" ->
                    "<E, T : Any> IterableLikeContains.CheckerStepLogic<E, T, InAnyOrderSearchBehaviour>" to
                        "entryPointStepLogic.container.getImpl"
                "IterableLikeContainsAssertions.kt" ->
                    "<E, T : Any, S : IterableLikeContains.SearchBehaviour> IterableLikeContains.EntryPointStepLogic<E, T, S>" to
                        "container.getImpl"
                else -> throw IllegalStateException("define a configuration for this new file this new file $fileNameAsString in build.gradle.kts of atrium-logic for createGenerateLogicTaskForCommon")
            }
        },
        "/creating/maplike/contains/creators" to { path ->

            when (val fileNameAsString = path.fileName.toString()) {
                "MapLikeContainsAssertions.kt" ->
                    "<K, V, T : Any, S : MapLikeContains.SearchBehaviour> MapLikeContains.EntryPointStepLogic<K, V, T, S>" to
                        "container.getImpl"
                else -> throw IllegalStateException("define a configuration for this new file this new file $fileNameAsString in build.gradle.kts of atrium-logic for createGenerateLogicTaskForCommon")
            }
        }
    )
)
