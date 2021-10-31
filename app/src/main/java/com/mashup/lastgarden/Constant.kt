package com.mashup.lastgarden

object Constant {
    /**
     *  TODO: Prefix id는 각 entity 별 id 형식이 같아서 오는 에러를 해결하기 위함입니다.
     *  서버에 각 entity id에 prefix를 붙여주는 방향으로 요청해보고 변경되면 아래 prefix constants는
     *  지워야 합니다.
     */
    const val PREFIX_PERFUME_ID = "P"
    const val PREFIX_STORY_ID = "S"
}