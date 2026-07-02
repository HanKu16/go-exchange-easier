export type CreateChatReportRequest = {
    description?: string;
    reason: string;
    reportedUserId: number;
    roomId: string;
}