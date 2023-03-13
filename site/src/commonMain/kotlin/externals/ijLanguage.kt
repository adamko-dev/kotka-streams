@file:Suppress("PackageDirectoryMismatch")

package org.intellij.lang.annotations


@Retention(AnnotationRetention.BINARY)
@Target(
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.PROPERTY_SETTER,
  AnnotationTarget.FIELD,
  AnnotationTarget.VALUE_PARAMETER,
  AnnotationTarget.LOCAL_VARIABLE,
  AnnotationTarget.ANNOTATION_CLASS
)
annotation class Language(
  val value: String,
  val prefix: String = "",
  val suffix: String = ""
)
