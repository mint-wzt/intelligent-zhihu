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
                    prop="username"
                    label="用户名"
                    width="180"
            >
            </el-table-column>
            <el-table-column
                    prop="role"
                    label="权限"
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
        name: "TheUserManage",
        data () {
            return {
                tableData:[
                    {
                        id: "dsgfg",
                        role: "dghdfgdfg",
                        username: "sss",
                    },
                    {
                        id: "asdfggdh",
                        role: "fghfghfg",
                        username: "wwww",
                    }
                ]
            }
        }
    }
</script>

<style scoped>

</style>