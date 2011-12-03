package silAST.programs {

import silAST.source.SourceLocation
import collection.mutable.HashMap
import silAST.domains.DomainFactory
import silAST.expressions.ExpressionFactory
import symbols.{ReferenceField, Field, NonReferenceField}
import silAST.types.{DataTypeSequence, NonReferenceDataType, DataType}
import silAST.methods.MethodFactory

final class ProgramFactory(
                            val name: String
                            ) extends NodeFactory with ExpressionFactory
{
  //////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////
  def getDomainFactory(name: String): DomainFactory =
    domainFactories.getOrElseUpdate(name, new DomainFactory(this, name))

  //////////////////////////////////////////////////////////////////////////
  def getMethodFactory(name: String): MethodFactory =
    methodFactories.getOrElseUpdate(name, new MethodFactory(this, name))


  //////////////////////////////////////////////////////////////////////////
  //@Symbol construction
  //////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////
  def defineDomainField(sl: SourceLocation, name: String, dataType: NonReferenceDataType): Field = {
    require(dataTypes.contains(dataType))
    defineField(new NonReferenceField(sl, name, dataType))
  }

  //////////////////////////////////////////////////////////////////////////
  def defineReferenceField(sl: SourceLocation, name: String): Field = {
    defineField(new ReferenceField(sl, name))
  }

  def defineField(field: Field): Field = {
    require(!fields.contains(field.name))

    fields += (field.name -> field)

    field
  }

  //////////////////////////////////////////////////////////////////////////
  def defineDataTypeSequence(types: List[DataType]): DataTypeSequence = {
    require(types.forall(dataTypes.contains(_)))

    dataTypeSequences.getOrElseUpdate(types, new DataTypeSequence(types))
  }

  //////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////
  private val domainFactories = new HashMap[String, DomainFactory]
  private val methodFactories = new HashMap[String, MethodFactory]

}

}

