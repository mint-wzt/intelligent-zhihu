<template>
    <div>
        <the-home-nav/>
        <el-row style="margin-top: 2rem;">
            <el-col :offset="4" :span="20">
                <div>sda </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue'
    export default {
        name: "TheQuestionEditPage",
        components: {
            TheHomeNav
        }
    }
</script>

<style scoped>

</style>