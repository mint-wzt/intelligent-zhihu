<template>
    <div>
        <el-table
            :data="tableData"
            style="width: 100%;"
        >
            <el-table-column
                prop="id"
                label="ID"
                width="180"
            >
            </el-table-column>
            <el-table-column
                prop="title"
                label="标题"
                width="180"
            >
            </el-table-column>
            <el-table-column
                    prop="author"
                    label="作者"
                    width="180"
            >
            </el-table-column>
            <el-table-column
                    label="操作"
            >
                <el-button type="danger">删除</el-button>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    export default {
        name: "TheArticleManage",
        data () {
            return {
                tableData:[
                    {
                        id: "dsgfg",
                        title: "dghdfgdfg",
                        author: "sss",
                    },
                    {
                        id: "asdfggdh",
                        title: "fghfghfg",
                        author: "wwww",
                    }
                ]
            }
        }
    }
</script>

<style scoped>

</style>