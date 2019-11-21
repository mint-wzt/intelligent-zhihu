<template>
    <div>
        <div>{{data}} ???</div>
<!--        <div v-if="items.length === 0">没有数据???</div>-->
<!--        <md-card v-for="item in items" :key="item.id">-->
<!--            <md-card-header>{{item.title}}</md-card-header>-->
<!--            <md-card-content>-->
<!--                {{item.content}}-->
<!--            </md-card-content>-->
<!--        </md-card>-->
    </div>
</template>

<script>
    export default {
        name: "TheRecommend",
        props: {
            data: {
                type: String
            }
        }
    }
</script>

<style scoped>

</style>