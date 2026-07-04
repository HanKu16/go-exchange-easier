export type CreateChatReportRequest = {
    description: string | null;
    reason: string;
    reportedUserId: string;
    roomId: string;
}