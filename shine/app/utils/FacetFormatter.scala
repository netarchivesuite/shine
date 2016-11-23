package utils

object FacetFormatter {

  def getFacetLimit(facetName: String, facetMap: Map[String, uk.bl.wa.shine.model.FacetValue]): Int = {
    if (facetMap != null) {
      facetMap.get(facetName) match {
        case Some(value) => {
          value.getLimit.toInt
        }
        case None => {
          5
        }
      }
    } else {
      5
    }
  }
}